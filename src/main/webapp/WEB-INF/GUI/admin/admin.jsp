<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*" %>
<%-- chiedi all'header di includere un CSS specifico per questa pagina --%>
<% pageContext.setAttribute("pageCss", "/css/admin_form.css", PageContext.PAGE_SCOPE); %>
<!DOCTYPE html>
<html lang="it">
<%@ include file="../includes/header.jspf" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin_form.css">

<div class="admin-form-wrapper">
    <div class="container">
        <h2>Inserisci nuovo Museo</h2>
        <p>Compila i campi per aggiungere un museo al sistema.</p>

        <form id="addMuseoForm" class="admin-form" action="${pageContext.request.contextPath}/admin/AddMuseoServlet" method="post" novalidate>
            <div class="form-row">
                <label for="imageUrl">URL Immagine</label>
                <input type="url" id="imageUrl" name="imageUrl" placeholder="https://esempio.com/immagine.jpg" required>
            </div>

            <div class="form-row">
                <label for="nome">Nome del museo</label>
                <input type="text" id="nome" name="nome" placeholder="Museo Nazionale..." required>
            </div>
            <div class="form-row">
                <label for="citta">Città</label>
                <input type="text" id="citta" name="citta" placeholder="Napoli" required>
            </div>

            <div class="form-row">
                <label for="descrizione">Descrizione</label>
                <textarea id="descrizione" name="descrizione" rows="5" placeholder="Breve descrizione del museo" required></textarea>
            </div>

            <div class="form-row">
                <label for="indirizzo">Indirizzo</label>
                <input type="text" id="indirizzo" name="indirizzo" placeholder="Via Roma 1, Napoli" required>
            </div>

            <fieldset class="ticket-prices">
                <legend>Prezzi biglietti</legend>

                <div class="ticket-grid">
                    <div class="form-row">
                        <label for="prezzoStandard">Standard (€)</label>
                        <input type="number" id="prezzoStandard" name="prezzoStandard" step="0.01" min="0" required>
                    </div>

                    <div class="form-row">
                        <label for="prezzoRidotto">Ridotto (€)</label>
                        <input type="number" id="prezzoRidotto" name="prezzoRidotto" step="0.01" min="0" required>
                    </div>

                    <div class="form-row">
                        <label for="prezzoSaltaFila">Salta Fila (€)</label>
                        <input type="number" id="prezzoSaltaFila" name="prezzoSaltaFila" step="0.01" min="0" required>
                    </div>
                </div>

                <div class="form-row" style="margin-top:16px;display:flex;align-items:center; gap:10px; flex-direction: row">
                    <input type="checkbox" id="tourGuidato" name="tourGuidato" value="true">
                    <label for="tourGuidato">Tour guidato disponibile</label>
                </div>
            </fieldset>

            <div class="form-actions">
                <button type="submit" class="btn-save">Salva Museo</button>
                <button type="reset" class="btn-reset">Reset</button>
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

            // basic URL pattern check
            const url = document.getElementById('imageUrl').value;
            try {
                if (url && !(new URL(url))) {
                    // fallthrough
                }
            } catch(err){
                e.preventDefault();
                message.textContent = 'Inserisci un URL immagine valido.';
                return;
            }

            // leave server-side to perform full validation and persistence
        });
    })();
</script>


<%@ include file="../includes/footer.jspf" %>
