<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tld/struts-tiles.tld" prefix="tiles" %>

<tiles:insert page="/pages/baseLayout.jsp" flush="true">
    <tiles:put name="title" value="Tiles Example" />
    <tiles:put name="header" value="/pages/header.jsp" />
    <tiles:put name="menu" value="/pages/menu.jsp" />
    <tiles:put name="body" value="/pages/body.jsp" />
    <tiles:put name="footer" value="/pages/footer.jsp" />
</tiles:insert>