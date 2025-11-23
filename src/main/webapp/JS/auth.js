(function () {
    'use strict';

    // Helper per mostrare messaggi sotto i campi
    function showFieldError(field, message) {
        let err = field.parentElement.querySelector('.field-error');
        if (!err) {
            err = document.createElement('div');
            err.className = 'field-error';
            field.parentElement.appendChild(err);
        }
        err.textContent = message;
        field.classList.add('invalid');
    }

    function clearFieldError(field) {
        let err = field.parentElement.querySelector('.field-error');
        if (err) err.remove();
        field.classList.remove('invalid');
    }

    function validateEmail(email) {
        return /^[A-Za-z0-9+_.-]+@(.+)$/.test(email);
    }

    // Password pattern (same policy usata in servlet): almeno 8 caratteri, almeno una maiuscola, almeno un numero, almeno un carattere speciale
    const pwPattern = /^(?=.*[A-Z])(?=.*\d)(?=.*[^A-Za-z0-9]).{8,}$/;

    // Funzione che aggiorna la UI della pw-policy
    function updatePasswordPolicyUI(password) {
        const policy = document.getElementById('pw-policy');
        if (!policy) return;
        const items = policy.querySelectorAll('li[data-req]');
        items.forEach(item => {
            const req = item.getAttribute('data-req');
            let met = false;
            switch (req) {
                case 'length':
                    met = password.length >= 8;
                    break;
                case 'uppercase':
                    met = /[A-Z]/.test(password);
                    break;
                case 'number':
                    met = /\d/.test(password);
                    break;
                case 'special':
                    // usa una classe negata per identificare qualsiasi carattere non alfanumerico
                    met = /[^A-Za-z0-9]/.test(password);
                    break;
            }
            if (met) {
                item.classList.remove('not-met');
                item.classList.add('met');
            } else {
                item.classList.remove('met');
                item.classList.add('not-met');
            }
        });
    }

    // Login form validation
    const loginForm = document.querySelector('.auth-form[action$="/login"]');
    if (loginForm) {
        loginForm.addEventListener('submit', function (e) {
            let valid = true;
            const email = loginForm.querySelector('input[name="email"]');
            const password = loginForm.querySelector('input[name="password"]');

            clearFieldError(email);
            clearFieldError(password);

            if (!email.value || !validateEmail(email.value)) {
                showFieldError(email, 'Inserisci una email valida');
                valid = false;
            }
            if (!password.value) {
                showFieldError(password, 'Inserisci la password');
                valid = false;
            }

            if (!valid) {
                e.preventDefault();
            }
        });
    }

    // Registration form validation
    const regForm = document.querySelector('.auth-form[action$="/registrazione"]');
    if (regForm) {
        const nome = regForm.querySelector('input[name="nome"]');
        const cognome = regForm.querySelector('input[name="cognome"]');
        const email = regForm.querySelector('input[name="email"]');
        const password = regForm.querySelector('input[name="password"]');
        const conferma = regForm.querySelector('input[name="confermaPassword"]');
        const termini = regForm.querySelector('input[name="accettaTermini"]');

        // live update password policy while user types
        if (password) {
            password.addEventListener('input', function () {
                updatePasswordPolicyUI(password.value);
                // clear any previous password-specific errors
                clearFieldError(password);
            });
        }

        // live check for password confirmation
        if (conferma) {
            conferma.addEventListener('input', function () {
                clearFieldError(conferma);
                if (password && password.value && conferma.value && password.value !== conferma.value) {
                    // don't block submit here, just show inline hint
                    let err = conferma.parentElement.querySelector('.field-error');
                    if (!err) {
                        err = document.createElement('div');
                        err.className = 'field-error';
                        conferma.parentElement.appendChild(err);
                    }
                    err.textContent = 'Le password non coincidono';
                } else {
                    let err = conferma.parentElement.querySelector('.field-error');
                    if (err) err.remove();
                }
            });
        }

        regForm.addEventListener('submit', function (e) {
            let valid = true;

            [nome, cognome, email, password, conferma].forEach(clearFieldError);

            if (!nome.value.trim()) {
                showFieldError(nome, 'Il nome è obbligatorio');
                valid = false;
            }
            if (!cognome.value.trim()) {
                showFieldError(cognome, 'Il cognome è obbligatorio');
                valid = false;
            }
            if (!email.value || !validateEmail(email.value)) {
                showFieldError(email, 'Inserisci una email valida');
                valid = false;
            }
            if (!password.value || password.value.length < 8) {
                showFieldError(password, 'La password deve essere almeno di 8 caratteri');
                valid = false;
            }
            if (password.value !== conferma.value) {
                showFieldError(conferma, 'Le password non coincidono');
                valid = false;
            }

            // aggiunta: controllo pattern password lato client (UX) - la validazione definitiva resta in servlet
            if (password.value && !pwPattern.test(password.value)) {
                showFieldError(password, 'La password non rispetta i requisiti');
                updatePasswordPolicyUI(password.value);
                valid = false;
            }

            if (!termini.checked) {
                showFieldError(termini, 'Devi accettare i termini');
                valid = false;
            }

            // Nota: controllo email unica è fatto lato server. Se vuoi AJAX, implementa endpoint /api/check-email

            if (!valid) {
                e.preventDefault();
            }
        });
    }

    // Sticky toggle per il pannello .auth-info
    const authInfo = document.querySelector('.auth-info');
    let stickyEnabled = false; // disabilitato per default per evitare che si muova con lo scroll

    function onScrollToggle() {
        if (!authInfo) return;
        if (window.scrollY > 100) {
            authInfo.classList.add('sticky');
        } else {
            authInfo.classList.remove('sticky');
        }
    }

    // Expose a toggle function in case some page wants to enable sticky behavior
    window.authSticky = function (enable) {
        stickyEnabled = !!enable;
        if (stickyEnabled) {
            window.addEventListener('scroll', onScrollToggle);
            onScrollToggle();
        } else {
            window.removeEventListener('scroll', onScrollToggle);
            if (authInfo) authInfo.classList.remove('sticky');
        }
    };

    // Default: ensure sticky is disabled (so it doesn't move on scroll)
    window.authSticky(false);

})();
