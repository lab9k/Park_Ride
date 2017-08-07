<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="currentPage" value="${pageContext.request.servletPath}" />

<nav class="navbar navbar-inverse ">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${contextPath}/">P&R Planner</a>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li ${currentPage == "/WEB-INF/views/home.jsp" ? 'class="active"' : ''}><a href="${contextPath}/">Planner</a></li>
                <li ${currentPage == "/WEB-INF/views/overview.jsp" ? 'class="active"' : ''}><a href="${contextPath}/overview">Overzicht P&amp;R locaties</a></li>
                <li><a target="_blank" href="https://www.delijn.be/nl/vervoerbewijzen/">De Lijn</a></li>
                <li><a target="_blank" href="https://mobiliteit.stad.gent/met-de-fiets/fiets-huren">Huurfietsen</a></li>
                <li><a target="_blank" href="https://stad.gent/mobiliteitsplan">Mobiliteitsplan Gent</a></li>
                <li ${currentPage == "/WEB-INF/views/faq.jsp" ? 'class="active"' : ''}><a href="${contextPath}/faq">FAQ</a></li>
            </ul>
        </div>
    </div>
</nav>