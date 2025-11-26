(function initSearch() {
    // Try to get elements; if not present, wait for DOMContentLoaded
    function doInit() {
        const input = document.getElementById('search-box');
        const suggestionBox = document.getElementById('suggestions');
        const inputWrap = document.querySelector('.input-wrap');
        const headerEl = document.querySelector('header');
        const contextPath = headerEl ? (headerEl.dataset.contextPath || '') : '';

        if (!input || !suggestionBox || !inputWrap) {
            console.warn('Homepage.js: elementi della search non trovati ancora, ritenterò al DOMContentLoaded');
            return false;
        }

        console.info('Homepage.js inizializzato', {contextPath});

        let timeout = null;

        function showSuggestions() {
            suggestionBox.classList.add('show');
            suggestionBox.style.display = 'block';
            inputWrap.classList.add('active');
        }

        function hideSuggestions() {
            suggestionBox.classList.remove('show');
            suggestionBox.style.display = 'none';
            inputWrap.classList.remove('active');
            suggestionBox.innerHTML = '';
        }

        input.addEventListener('input', function() {
            clearTimeout(timeout);
            const value = this.value.trim();

            if (value.length === 0) {
                hideSuggestions();
                return;
            }

            const url = `${contextPath}/SuggerimentiBarraServlet?query=${encodeURIComponent(value)}`;
            console.debug('Homepage.js fetch', url);

            timeout = setTimeout(() => {
                fetch(url)
                    .then(response => response.text())
                    .then(text => {
                        // try to parse JSON safely; log on error
                        let results;
                        try {
                            results = JSON.parse(text);
                        } catch (e) {
                            console.warn('Homepage.js: risposta non JSON, provo fallback CSV', text);
                            // fallback: if response is comma-separated, turn into array
                            if (typeof text === 'string' && text.indexOf(',') !== -1) {
                                results = text.split(',').map(s => s.trim()).filter(Boolean);
                            } else if (text.trim().length === 0) {
                                results = [];
                            } else {
                                // single value fallback
                                results = [text.trim()];
                            }
                        }

                        console.debug('Homepage.js: risultati suggerimenti', results);

                        suggestionBox.innerHTML = '';

                        if (!Array.isArray(results) || results.length === 0) {
                            hideSuggestions();
                            return;
                        }

                        results.forEach(item => {
                            const li = document.createElement('li');
                            li.textContent = item;
                            li.classList.add('suggestion-item');
                            li.setAttribute('role', 'option');
                            li.setAttribute('tabindex', '0');

                            li.addEventListener('click', () => {
                                input.value = item;
                                if (input.form) input.form.submit();
                            });

                            li.addEventListener('keydown', (ev) => {
                                if (ev.key === 'Enter' || ev.key === ' ') {
                                    ev.preventDefault();
                                    input.value = item;
                                    if (input.form) input.form.submit();
                                }
                            });

                            suggestionBox.appendChild(li);
                        });

                        showSuggestions();
                    })
                    .catch(error => {
                        console.error('Homepage.js fetch error:', error);
                        hideSuggestions();
                    });
            }, 300);

        });

        // Hide suggestions when clicking outside the input-wrap or suggestions
        document.addEventListener('click', function(event) {
            if (!inputWrap.contains(event.target)) {
                hideSuggestions();
            }
        });

        // Hide suggestions on Escape key
        document.addEventListener('keydown', function(event) {
            if (event.key === 'Escape') {
                hideSuggestions();
                try { input.blur(); } catch(e){}
            }
        });

        // Optional: hide when input loses focus (but allow clicks on suggestions)
        input.addEventListener('blur', function() {
            // small timeout to allow click event on suggestion to fire
            setTimeout(() => {
                if (document.activeElement && document.activeElement.closest && document.activeElement.closest('#suggestions')) {
                    return;
                }
                hideSuggestions();
            }, 150);
        });

        return true;
    }

    // previous invocation: if (!doInit()) { document.addEventListener('DOMContentLoaded', ... ) }
    // Replace with readyState-aware init
    function ensureInit() {
        if (document.readyState === 'loading') {
            document.addEventListener('DOMContentLoaded', function() {
                const ok = doInit();
                if (!ok) console.error('Homepage.js: ancora non riuscito a inizializzare gli elementi della search');
            });
        } else {
            const ok = doInit();
            if (!ok) console.error('Homepage.js: ancora non riuscito a inizializzare gli elementi della search');
        }
    }

    ensureInit();
})();
