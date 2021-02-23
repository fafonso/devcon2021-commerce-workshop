<%@ include file="/init.jsp" %>

<%
CPContentHelper cpContentHelper = (CPContentHelper)request.getAttribute(CPContentWebKeys.CP_CONTENT_HELPER);

CPCatalogEntry cpCatalogEntry = cpContentHelper.getCPCatalogEntry(request);

CPSku cpSku = cpContentHelper.getDefaultCPSku(cpCatalogEntry);
long cpDefinitionId = cpCatalogEntry.getCPDefinitionId();
%>

<div class="col-md-4">
	<div class="card">
		<a class="aspect-ratio" href="<%= cpContentHelper.getFriendlyURL(cpCatalogEntry, themeDisplay) %>">

			<%
			String img = cpCatalogEntry.getDefaultImageFileUrl();
			%>

			<c:if test="<%= Validator.isNotNull(img) %>">
				<img class="aspect-ratio-item-center-middle aspect-ratio-item-fluid" src="<%= img %>" />
			</c:if>
		</a>

		<div class="card-row card-row-padded card-row-valign-top">
			<div class="card-col-content" style="text-align: center;">
				<a class="truncate-text" href="<%= cpContentHelper.getFriendlyURL(cpCatalogEntry, themeDisplay) %>">
					<h3><%= cpCatalogEntry.getName() %></h3>
				</a>
			</div>
		</div>

		<div class="price-section" style="text-align: center;">
			<c:choose>
					<c:when test="<%= cpSku != null %>">
						<div class="price">
							<liferay-commerce:price
								CPDefinitionId="<%= cpDefinitionId %>"
								CPInstanceId="<%= cpSku.getCPInstanceId() %>"
							/>
						</div>
					</c:when>
					<c:otherwise>
						<div class="price" data-text-cp-instance-price="">Price Ranges</div>
					</c:otherwise>
				</c:choose>
		</div>

		<div class="btn-group" style="padding: 5px;">
			<div class="disabledbutton">
				<liferay-commerce:quantity-input CPDefinitionId="<%= cpCatalogEntry.getCPDefinitionId() %>" useSelect="<%= false %>" />
			</div>

			<a href="<%= cpContentHelper.getFriendlyURL(cpCatalogEntry, themeDisplay) %>">
				<button class="btn btn-default btn-lg taglib-add-to-cart" type="button">Choose Options</button>
			</a>
		</div>
	</div>
</div>

<style>
.disabledbutton {
	pointer-events: none;
	opacity: 0.4;
}
</style>