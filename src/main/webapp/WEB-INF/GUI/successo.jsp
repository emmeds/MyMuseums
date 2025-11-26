<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- Imposta il CSS specifico --%>
<c:set var="pageCss" value="css/successo.css" scope="request" />

<!DOCTYPE html>
<html lang="it">
<%@ include file="includes/header.jspf" %>

<body>

<div class="success-wrapper">
    <div class="success-card">
        <span class="success-icon">🎉</span>
        <h1>Acquisto Completato!</h1>

        <p>
            Il tuo ordine <strong>#<c:out value="${idOrdine}"/></strong> è stato registrato con successo.<br>
            Ti abbiamo inviato una email di conferma.
        </p>

        <a href="${pageContext.request.contextPath}/" class="btn-home">Torna alla Home</a>
    </div>
</div>

<%@ include file="includes/footer.jspf" %>
</body>
</html>
