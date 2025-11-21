<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registrati - MyMuseums</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/Homepage.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/auth/registrazione_login.css">
</head>
<body>
    <header class="header">
        <div class="container">
            <div class="logo">
                <h1>🏛️ MyMuseums</h1>
            </div>
            <nav class="nav">
                <ul>
                    <li><a href="${pageContext.request.contextPath}/">Home</a></li>
                    <li><a href="#musei">Musei</a></li>
                    <li><a href="#eventi">Eventi</a></li>
                    <li><a href="#contatti">Contatti</a></li>
                </ul>
            </nav>
            <div class="header-actions">
                <a href="${pageContext.request.contextPath}/login" style="text-decoration: none;"><button class="btn-secondary">Accedi</button></a>
                <a href="${pageContext.request.contextPath}/registrazione" style="text-decoration: none;"><button class="btn-primary">Registrati</button></a>
            </div>
        </div>
    </header>

    <!-- Auth Section -->
    <section class="auth-section">
        <div class="auth-container">
            <div class="auth-card">
                <div class="auth-header">
                    <h2>Registrati</h2>
                    <p>Crea un account per iniziare a prenotare</p>
                </div>

                <form action="${pageContext.request.contextPath}/registrazione" method="post" class="auth-form">
                    <div class="form-row">
                        <div class="form-group">
                            <label for="nome">Nome</label>
                            <input type="text" id="nome" name="nome" required placeholder="Inserisci il tuo nome">
                        </div>

                        <div class="form-group">
                            <label for="cognome">Cognome</label>
                            <input type="text" id="cognome" name="cognome" required placeholder="Inserisci il tuo cognome">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="email">Email</label>
                        <input type="email" id="email" name="email" required placeholder="esempio@email.com">
                    </div>

                    <div class="form-group">
                        <label for="password">Password</label>
                        <input type="password" id="password" name="password" required placeholder="Minimo 8 caratteri">
                        <small class="form-hint">La password deve contenere almeno 8 caratteri</small>
                    </div>

                    <div class="form-group">
                        <label for="confermaPassword">Conferma Password</label>
                        <input type="password" id="confermaPassword" name="confermaPassword" required placeholder="Ripeti la password">
                    </div>

                    <div class="form-group checkbox-group">
                        <input type="checkbox" id="accettaTermini" name="accettaTermini" required>
                        <label for="accettaTermini">Accetto i <a href="#">Termini e Condizioni</a> e la <a href="#">Privacy Policy</a></label>
                    </div>

                    <button type="submit" class="btn-auth">Registrati</button>

                    <div class="auth-footer">
                        <p>Hai già un account? <a href="${pageContext.request.contextPath}/login">Accedi</a></p>
                    </div>
                </form>
            </div>

            <div class="auth-info">
                <h3>Unisciti a MyMuseums</h3>
                <ul class="benefits-list">
                    <li>
                        <span class="benefit-icon">✓</span>
                        <div>
                            <strong>Accesso immediato</strong>
                            <p>Prenota i tuoi biglietti in pochi click</p>
                        </div>
                    </li>
                    <li>
                        <span class="benefit-icon">✓</span>
                        <div>
                            <strong>Offerte esclusive</strong>
                            <p>Sconti riservati solo agli utenti registrati</p>
                        </div>
                    </li>
                    <li>
                        <span class="benefit-icon">✓</span>
                        <div>
                            <strong>Storico acquisti</strong>
                            <p>Gestisci tutti i tuoi biglietti in un unico posto</p>
                        </div>
                    </li>
                    <li>
                        <span class="benefit-icon">✓</span>
                        <div>
                            <strong>Newsletter culturale</strong>
                            <p>Rimani aggiornato su eventi e mostre</p>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </section>

<%@ include file="../includes/footer.jspf" %>
