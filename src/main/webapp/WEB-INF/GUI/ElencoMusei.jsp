<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="pageCss" value="css/Homepage.css,css/elencomusei.css" scope="request" />

<%@ include file="includes/header.jspf" %>

<section class="container" style="padding:2rem 0;">
    <div class="container">
        <h2 class="section-title">Risultati ricerca per: <c:out value="${searchQuery}"/></h2>

        <c:choose>
            <c:when test="${empty risultati}">
                <p>Nessun museo trovato per la tua ricerca.</p>
            </c:when>
            <c:otherwise>
                <div class="museums-grid">
                    <c:forEach items="${risultati}" var="museo">
                        <a href="${pageContext.request.contextPath}/DettagliMuseoServlet?id=${museo.idMuseo}" class="card-link">
                            <div class="museum-card">
                                <div class="museum-image" style="height:160px;">
                                    <img src="${pageContext.request.contextPath}${museo.immagine}" alt="${museo.nome}" style="width:100%;height:100%;object-fit:cover;display:block;"/>
                                </div>
                                <div class="museum-content">
                                    <h3><c:out value="${museo.nome}"/></h3>
                                    <p class="museum-location">📍 <c:out value="${museo.citta}"/></p>
                                    <p class="museum-description"><c:out value="${museo.descrizione}"/></p>
                                    <div class="museum-footer">
                                        <span class="price">Da €<c:out value="${museo.prezzoTourGuidato}"/></span>
                                        <button class="btn-primary">Dettagli</button>
                                    </div>
                                </div>
                            </div>
                        </a>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>

    </div>
</section>

<%@ include file="includes/footer.jspf" %>
