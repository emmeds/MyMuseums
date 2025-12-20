<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<%@ include file="../includes/header.jspf" %>
    <title>Registrati - MyMuseums</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/auth/registrazione_login.css">

    <!-- Auth Section -->
    <section class="auth-section">
        <div class="auth-container">
            <div class="auth-card">
                <div class="auth-header">
                    <h2>Registrati</h2>
                    <p>Crea un account per iniziare ad acquistare</p>
                </div>

                <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
                <% String successMessage = (String) request.getAttribute("successMessage"); %>
                <% if (errorMessage != null) { %>
                    <div class="error-message">
                        <%= errorMessage %>
                    </div>
                <% } else if (successMessage != null) { %>
                    <div class="success-message">
                        <%= successMessage %>
                    </div>
                <% } %>

                <form action="${pageContext.request.contextPath}/registrazione" method="post" class="auth-form">
                    <div class="form-row">
                        <div class="form-group">
                            <label for="nome">Nome</label>
                            <input type="text" id="nome" name="nome" required placeholder="Inserisci il tuo nome"
                                   value="<%= request.getAttribute("nome") != null ? request.getAttribute("nome") : "" %>">
                        </div>

                        <div class="form-group">
                            <label for="cognome">Cognome</label>
                            <input type="text" id="cognome" name="cognome" required placeholder="Inserisci il tuo cognome"
                                   value="<%= request.getAttribute("cognome") != null ? request.getAttribute("cognome") : "" %>">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="email">Email</label>
                        <input type="email" id="email" name="email" required placeholder="esempio@email.com"
                               value="<%= request.getAttribute("email") != null ? request.getAttribute("email") : "" %>">
                    </div>

                    <div class="form-group">
                        <label for="password">Password</label>
                        <input type="password" id="password" name="password" required placeholder="Minimo 8 caratteri">
                        <!-- Password policy: visuale per l'utente -->
                        <ul class="pw-policy" id="pw-policy">
                            <li data-req="length" class="not-met">Almeno 8 caratteri</li>
                            <li data-req="uppercase" class="not-met">Almeno una lettera maiuscola (A-Z)</li>
                            <li data-req="number" class="not-met">Almeno un numero (0-9)</li>
                            <li data-req="special" class="not-met">Almeno un carattere speciale (!@#$...)</li>
                        </ul>
                    </div>

                    <div class="form-group">
                        <label for="confermaPassword">Conferma Password</label>
                        <input type="password" id="confermaPassword" name="confermaPassword" required placeholder="Ripeti la password">
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
                            <p>Acquista i tuoi biglietti in pochi click</p>
                        </div>
                    </li>
                    <li>
                        <span class="benefit-icon">✓</span>
                        <div>
                            <strong>Storico acquisti</strong>
                            <p>Visualizza tutti i tuoi biglietti in un unico posto</p>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </section>

    <script src="${pageContext.request.contextPath}/JS/auth.js"></script>

<%@ include file="../includes/footer.jspf" %>
