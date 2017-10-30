<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<script type="text/javascript">  
     var ctx="${pageContext.request.contextPath}";  
</script>

<script type="text/javascript" src="${ctx}/resources/js/common.js"></script>
<%-- <script type="text/javascript" src="${ctx}/resources/js/common.js"></script> --%>

<link rel="stylesheet" type="text/css" href="${ctx}/resources/thrlib/jquery-easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/resources/thrlib/jquery-easyui/themes/icon.css">
<script type="text/javascript" src="${ctx}/resources/thrlib/jquery-easyui/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/thrlib/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/thrlib/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>