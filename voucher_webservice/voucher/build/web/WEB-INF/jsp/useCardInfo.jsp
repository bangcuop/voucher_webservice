<%@page import="com.vss.cardservice.dto.UseCardInfo"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    UseCardInfo uci = (UseCardInfo) request.getAttribute("useCardInfo");
    if (uci.getUrlRedirect() == null || uci.getUrlRedirect().isEmpty()) {
%>
${useCardInfo.responseToPartner}
<%    } else {
        response.sendRedirect("http://" + uci.getUrlRedirect() + "?result=" + uci.getResponseToPartner());
    }
%>