<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="nl">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
        <meta name="description" content="">
        <meta name="author" content="">

        <!-- Favicon for all platforms. See realfavicongenerator.net for FAQ. -->
        <link rel="apple-touch-icon" sizes="180x180" href="${contextPath}/apple-touch-icon.png">
        <link rel="icon" type="image/png" href="${contextPath}/favicon-32x32.png" sizes="32x32">
        <link rel="icon" type="image/png" href="${contextPath}/favicon-16x16.png" sizes="16x16">
        <link rel="manifest" href="${contextPath}/manifest.json">
        <link rel="mask-icon" href="${contextPath}/safari-pinned-tab.svg" color="#7b7b7b">
        <meta name="theme-color" content="#ffffff">

        <title>Pagina niet gevonden - P&R Planner</title>

        <!-- Bootstrap core CSS -->
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">

        <!-- Custom CSS -->
        <link href="${contextPath}/resources/css/style.css" rel="stylesheet">

        <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
            <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
            <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
        <![endif]-->
    </head>

    <body>
        <div id="wrapper">
            <jsp:include page="includes/navbar.jsp" />

            <div id="content">
                <div class="container">
                    <div class="row">
                        <div class="col-xs-12 head-pane">
                            <div class="col-md-12 pane">
                                <h1>De pagina werd niet gevonden</h1>
                                <div>
                                    <p>                                        
                                        De pagina op dit adres kan niet gevonden worden.<br>
                                        Meestal is dit het gevolg van een foutieve of verouderde link.<br>
                                        Klik <a href="${contextPath}/">hier</a> om terug te gaan naar de hoofdpagina.<br>
                                        Sorry voor het ongemak.
                                    </p>
                                </div>
                                <%-- 
                                <div>
                                    <p><b>Exception:</b></p>
                                    <div class="exception">${exception}</div>
                                    <p><b>Stack trace:</b></p>
                                    <div class="exception">
                                        <c:forEach items="${exception.stackTrace}" var="trace">
                                            ${trace}<br>
                                        </c:forEach>
                                    </div>
                                </div>
                                --%>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <jsp:include page="includes/footer.jsp"/>
        </div>

        <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        <!-- If jQuery takes too long to load from Google's server, load from local server -->
        <script>window.jQuery || document.write('<script src="${contextPath}/resources/js/jquery.min.js"><\/script>');</script>

        <!-- Bootstrap core JS -->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    </body>
</html>