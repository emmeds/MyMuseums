<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Accedi - MyMuseums</title>
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
                    <h2>Accedi</h2>
                    <p>Bentornato! Inserisci le tue credenziali</p>
                </div>

                <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
                <% if (errorMessage != null) { %>
                    <div class="error-message">
                        <%= errorMessage %>
                    </div>
                <% } %>

                <form action="${pageContext.request.contextPath}/login" method="post" class="auth-form">
                    <div class="form-group">
                        <label for="email">Email</label>
                        <input type="email" id="email" name="email" required placeholder="esempio@email.com"
                               value="<%= request.getAttribute("email") != null ? request.getAttribute("email") : "" %>">
                    </div>

                    <div class="form-group">
                        <label for="password">Password</label>
                        <input type="password" id="password" name="password" required placeholder="Inserisci la tua password">
                    </div>

                    <div class="form-group checkbox-group">
                        <input type="checkbox" id="ricordami" name="ricordami">
                        <label for="ricordami">Ricordami</label>
                    </div>

                    <button type="submit" class="btn-auth">Accedi</button>

                    <div class="auth-links">
                        <a href="#" class="forgot-password">Password dimenticata?</a>
                    </div>

                    <div class="auth-footer">
                        <p>Non hai un account? <a href="${pageContext.request.contextPath}/registrazione">Registrati</a></p>
                    </div>
                </form>
            </div>

            <div class="auth-info">
                <h3>Bentornato su MyMuseums</h3>
                <ul class="benefits-list">
                    <li>
                        <span class="benefit-icon">🎫</span>
                        <div>
                            <strong>I tuoi biglietti</strong>
                            <p>Accedi a tutti i tuoi acquisti</p>
                        </div>
                    </li>
                    <li>
                        <span class="benefit-icon">⭐</span>
                        <div>
                            <strong>Preferiti</strong>
                            <p>Salva i musei che ami di più</p>
                        </div>
                    </li>
                    <li>
                        <span class="benefit-icon">🔔</span>
                        <div>
                            <strong>Notifiche</strong>
                            <p>Ricevi aggiornamenti su nuovi eventi</p>
                        </div>
                    </li>
                    <li>
                        <span class="benefit-icon">💳</span>
                        <div>
                            <strong>Metodi di pagamento</strong>
                            <p>Gestisci le tue carte salvate</p>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </section>

<%@ include file="../includes/footer.jspf" %>

