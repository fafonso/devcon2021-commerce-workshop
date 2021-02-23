<%@ include file="/init.jsp" %>

<%
CPContentHelper cpContentHelper = (CPContentHelper)request.getAttribute(CPContentWebKeys.CP_CONTENT_HELPER);

CPCatalogEntry cpCatalogEntry = cpContentHelper.getCPCatalogEntry(request);

CPSku cpSku = cpContentHelper.getDefaultCPSku(cpCatalogEntry);
long cpDefinitionId = cpCatalogEntry.getCPDefinitionId();
%>

<div class="col-md-4">
	<div class="card">
		
		<h3 style="white-space: nowrap;" >DEVCON 2021!</h3>
	
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
					<div class="price" data-text-cp-instance-price="">Option Dependant </div>
				</c:otherwise>
			</c:choose>
		</div>

		<%
		CPInstance cpInstance = cpContentHelper.getDefaultCPInstance(request);

		long cpInstanceId = 0;

		if (cpInstance != null) {
			cpInstanceId = cpInstance.getCPInstanceId();
		}

		String productContentId = renderResponse.getNamespace() + cpCatalogEntry.getCPDefinitionId()
				+ "ProductContent";
		String quantityInputId = renderResponse.getNamespace() + cpCatalogEntry.getCPDefinitionId() + "Quantity";
		%>

		<div class="btn-group" style="padding: 5px;">
			<liferay-commerce:quantity-input CPDefinitionId="<%= cpCatalogEntry.getCPDefinitionId() %>" useSelect="<%= false %>" />

			<c:choose>
				<c:when test="<%= cpSku != null %>">
					<liferay-commerce-cart:add-to-cart
						CPDefinitionId="<%= cpCatalogEntry.getCPDefinitionId() %>"
						CPInstanceId="<%= cpInstanceId %>"
						elementClasses="btn-lg btn-default"
						productContentId='<%= productContentId %>'
						taglibQuantityInputId='<%= quantityInputId %>'
					/>
				</c:when>
				<c:otherwise>
					<a href="<%= cpContentHelper.getFriendlyURL(cpCatalogEntry, themeDisplay) %>">
						<button class="btn btn-default btn-lg taglib-add-to-cart" type="button">Choose Options</button>
					</a>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</div>