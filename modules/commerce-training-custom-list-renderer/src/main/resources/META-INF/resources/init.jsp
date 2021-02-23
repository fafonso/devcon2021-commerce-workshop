<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/commerce" prefix="liferay-commerce" %><%@
taglib uri="http://liferay.com/tld/commerce-cart" prefix="liferay-commerce-cart" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>
<%@
page import="com.liferay.commerce.product.model.CPInstance" %>
<%@ page import="com.liferay.commerce.product.catalog.CPCatalogEntry" %><%@
page import="com.liferay.commerce.product.catalog.CPSku" %><%@
page import="com.liferay.commerce.product.content.constants.CPContentWebKeys" %><%@
page import="com.liferay.commerce.product.content.util.CPCompareContentHelper" %><%@
page import="com.liferay.commerce.product.content.util.CPContentHelper" %><%@
page import="com.liferay.commerce.product.model.CPAttachmentFileEntry" %><%@
page import="com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue" %><%@
page import="com.liferay.commerce.product.model.CPOptionCategory" %><%@
page import="com.liferay.commerce.product.model.CPSpecificationOption" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.repository.model.FileEntry" %><%@
page import="com.liferay.portal.kernel.util.Validator" %>
<liferay-theme:defineObjects />

<portlet:defineObjects />