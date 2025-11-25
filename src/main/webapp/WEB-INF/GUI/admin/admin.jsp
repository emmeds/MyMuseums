<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*" %>
<%-- chiedi all'header di includere un CSS specifico per questa pagina --%>
<% pageContext.setAttribute("pageCss", "/css/admin_form.css", PageContext.PAGE_SCOPE); %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Admin - Aggiungi Museo</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin_form.css">
</head>
<body>

<div class="admin-form-wrapper">
    <div class="log-out">
        <a href="${pageContext.request.contextPath}/logout">
            <button type="button" class="btn-primary">Logout Admin</button>
        </a>
    </div>
    <div class="container">
        <h2>Inserisci nuovo Museo</h2>
        <p>Compila i campi per aggiungere un museo al sistema.</p>

        <!-- Messaggi server-side -->
        <c:if test="${not empty successMessage}">
            <div class="alert alert-success" role="status" aria-live="polite">${successMessage}</div>
        </c:if>
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-error" role="alert" aria-live="polite">${errorMessage}</div>
        </c:if>

        <form id="addMuseoForm" class="admin-form" action="${pageContext.request.contextPath}/AddMuseoServlet" method="post" novalidate>
            <div class="form-row">
                <label for="imageUrl">URL Immagine</label>
                <input type="url" id="imageUrl" name="imageUrl" placeholder="https://esempio.com/immagine.jpg" required>
            </div>

            <div class="form-row">
                <label for="nome">Nome del museo</label>
                <input type="text" id="nome" name="nome" placeholder="Museo Nazionale..." required>
            </div>

            <div class="form-row">
                <label for="via">Via</label>
                <input type="text" id="via" name="via" placeholder="Via Roma, 1" required>
            </div>
            <div class="form-row">
                <label for="citta">Città</label>
                <input type="text" id="citta" name="citta" placeholder="Napoli" required>
            </div>
            <div class="form-row">
                <label for="cap">CAP</label>
                <input type="text" id="cap" name="cap" placeholder="80100" required pattern="\d{5}">
            </div>

            <div class="form-row">
                <label for="descrizione">Descrizione</label>
                <textarea id="descrizione" name="descrizione" rows="5" placeholder="Breve descrizione del museo" required></textarea>
            </div>



            <fieldset class="ticket-prices">
                <legend>Prezzi biglietti</legend>

                <div class="ticket-grid">
                    <div class="form-row">
                        <label for="prezzoStandard">Standard (€)</label>
                        <input type="number" id="prezzoStandard" name="prezzoStandard" step="5" min="0" required>
                    </div>

                    <div class="form-row">
                        <label for="prezzoRidotto">Ridotto (€)</label>
                        <input type="number" id="prezzoRidotto" name="prezzoRidotto" step="5" min="0" required>
                    </div>

                    <div class="form-row">
                        <label for="prezzoSaltaFila">Salta Fila (€)</label>
                        <input type="number" id="prezzoSaltaFila" name="prezzoSaltaFila" step="5" min="0" required>
                    </div>
                </div>


                <div class="form-row">
                    <label for="prezzoTourGuidato" style="width:50%;margin-top: 10px">Prezzo Tour-Guidato (€)</label>
                    <input type="number" id="prezzoTourGuidato" name="prezzoTourGuidato" step="5" min="0" required style="width:29%;">

                </div>
            </fieldset>

            <div class="form-actions">
                <button type="submit" class="btn-save">Salva Museo</button>

            </div>


            <div id="formMessage" class="form-message" aria-live="polite"></div>
        </form>
    </div>
</div>

<script>
    // Minima validazione client-side per migliorare UX: prezzi non negativi e required
    (function(){
        const form = document.getElementById('addMuseoForm');
        const message = document.getElementById('formMessage');

        form.addEventListener('submit', function(e){
            message.textContent = '';
            const pStd = parseFloat(document.getElementById('prezzoStandard').value || '0');
            const pRid = parseFloat(document.getElementById('prezzoRidotto').value || '0');
            const pSkip = parseFloat(document.getElementById('prezzoSaltaFila').value || '0');

            if (isNaN(pStd) || isNaN(pRid) || isNaN(pSkip)){
                e.preventDefault();
                message.textContent = 'I prezzi devono essere numeri validi.';
                return;
            }
            if (pStd < 0 || pRid < 0 || pSkip < 0){
                e.preventDefault();
                message.textContent = 'I prezzi non possono essere negativi.';
                return;
            }



            // leave server-side to perform full validation and persistence
        });
    })();
</script>

</body>
</html>
