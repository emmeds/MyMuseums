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
        regForm.addEventListener('submit', function (e) {
            let valid = true;
            const nome = regForm.querySelector('input[name="nome"]');
            const cognome = regForm.querySelector('input[name="cognome"]');
            const email = regForm.querySelector('input[name="email"]');
            const password = regForm.querySelector('input[name="password"]');
            const conferma = regForm.querySelector('input[name="confermaPassword"]');
            const termini = regForm.querySelector('input[name="accettaTermini"]');

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
