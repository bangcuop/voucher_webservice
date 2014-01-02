<%-- 
    Document   : useCardForm
    Created on : Jan 12, 2012, 2:27:48 PM
    Author     : zannami
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<form:form action="useCard.html" method="POST" id="frmUseCard">
    issuer: <form:input path="issuer"/>
            <br/>
    cardSerial <form:input path="cardSerial"/> <br/>
    cardCode <form:input path="cardCode"/> <br/>
    transRef <form:input path="transRef"/> <br/>
    partnerCode <form:input path="partnerCode"/> <br/>
    password <form:input path="password"/> <br/>
    signature <form:input path="signature"/> <br/>
    <input type="submit" value="useCard"/> <br/>
</form:form>

