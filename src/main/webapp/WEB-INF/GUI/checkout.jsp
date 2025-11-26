<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- Imposta il CSS specifico per questa pagina --%>
<c:set var="pageCss" value="css/checkout.css" scope="request" />

<!DOCTYPE html>
<html lang="it">
<%@ include file="includes/header.jspf" %>

<body>

<div class="container">
    <div class="checkout-container">
        <h2>Riepilogo e Pagamento</h2>

        <c:if test="${not empty error}">
            <div class="alert-error">
                ⚠️ <c:out value="${error}"/>
            </div>
        </c:if>

        <div class="summary-box">
            <h3>Museo: <c:out value="${sessionScope.nomeMuseoCheckout}"/></h3>

            <c:if test="${not empty sessionScope.ordineInCorso.biglietti}">
                <p>Data Visita: <c:out value="${sessionScope.ordineInCorso.biglietti[0].dataVisita}"/></p>
            </c:if>

            <c:if test="${not empty requestScope.msgTour}">
                <p><em><c:out value="${requestScope.msgTour}"/></em></p>
            </c:if>

            <hr style="border: 0; border-top: 1px solid rgba(0,0,0,0.1); margin: 1rem 0;">

            <div class="total-price">
                Totale: € <fmt:formatNumber value="${sessionScope.ordineInCorso.importoTotale}" type="number" minFractionDigits="2"/>
            </div>
        </div>

        <form action="${pageContext.request.contextPath}/AcquistoServlet" method="post">
            <input type="hidden" name="action" value="paga" />

            <div class="form-group">
                <label>Metodo di Pagamento</label>
                <select name="metodoPagamento" class="form-control">
                    <option value="carta">Carta di Credito</option>
                    <option value="paypal">PayPal</option>
                </select>
            </div>

            <div class="form-group">
                <label>Intestatario Carta</label>
                <input type="text" name="intestatario" class="form-control" placeholder="Es. Marco Rossi" required>
            </div>

            <div class="form-group">
                <label>Numero Carta</label>
                <input type="text" name="numeroCarta" class="form-control" placeholder="0000 0000 0000 0000" required pattern="\d{4}-?\d{4}-?\d{4}-?\d{4}" title="Inserisci 16 cifre">
            </div>

            <div style="display: flex; gap: 1rem;">
                <div class="form-group" style="flex: 1;">
                    <label>Scadenza</label>
                    <input type="text" name="scadenza" class="form-control" placeholder="MM/YY" required>
                </div>
                <div class="form-group" style="flex: 1;">
                    <label>CVC</label>
                    <input type="text" name="cvc" class="form-control" placeholder="123" required maxlength="3" pattern="\d{3}">
                </div>
            </div>

            <button type="submit" class="btn-pay">Conferma e Paga</button>
        </form>
    </div>
</div>

<%@ include file="includes/footer.jspf" %>
</body>
</html>