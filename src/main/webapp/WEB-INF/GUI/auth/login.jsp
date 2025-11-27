<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<%@ include file="../includes/header.jspf" %>
    <title>Accedi - MyMuseums</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/auth/registrazione_login.css">

    <!-- Auth Section -->
    <section class="auth-section">
        <div class="auth-container">
            <div class="auth-card">
                <div class="auth-header">
                    <h2>Accedi</h2>
                    <p>Bentornato! Inserisci le tue credenziali</p>
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



                    <button type="submit" class="btn-auth">Accedi</button>

                    

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

    <script src="${pageContext.request.contextPath}/JS/auth.js"></script>

<%@ include file="../includes/footer.jspf" %>
