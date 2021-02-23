package com.liferay.training.product.list.renderer.entry;

import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.commerce.product.catalog.CPCatalogEntry;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.content.constants.CPContentWebKeys;
import com.liferay.commerce.product.content.render.list.entry.CPContentListEntryRenderer;
import com.liferay.commerce.product.content.util.CPContentHelper;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;

/**
 * This class is used to determine which view should be rendered.
 * 
 * @author Filipe Afonso
 */
@Component(
	immediate = true,
	property = {
		"commerce.product.content.list.entry.renderer.key=" + CommerceTrainingCustomListEntryRenderer.KEY,
		"commerce.product.content.list.entry.renderer.order=" + Integer.MIN_VALUE,
		"commerce.product.content.list.entry.renderer.portlet.name=" + CPPortletKeys.CP_PUBLISHER_WEB,
		"commerce.product.content.list.entry.renderer.portlet.name=" + CPPortletKeys.CP_SEARCH_RESULTS,
		"commerce.product.content.list.entry.renderer.type=grouped",
		"commerce.product.content.list.entry.renderer.type=simple",
		"commerce.product.content.list.entry.renderer.type=virtual"
	},
	service = CPContentListEntryRenderer.class
)
public class CommerceTrainingCustomListEntryRenderer
	implements CPContentListEntryRenderer {

	public static final String KEY = "list-entry-devcon";

	/**
	 * Returns the value of the String KEY which is set in the class.
	 *
	 * @return the key of Renderer
	 */
	@Override
	public String getKey() {
		return KEY;
	}

	/**
	 * Returns the label of the class. It is the value which will be shown in the
	 * drop down list when choosing the renderer type.
	 *
	 * @param locale the Locale being used
	 * @return the label of Renderer
	 */
	@Override
	public String getLabel(Locale locale) {
		return KEY;
	}

	/**
	 * This method decides which jsp to load depending on certain characteristics of
	 * the product.
	 *
	 * @param httpServletRequest  the HttpServletRequest
	 * @param httpServletResponse the HttpServletResponse
	 *
	 */
	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		CPContentHelper cpContentHelper = (CPContentHelper) httpServletRequest
				.getAttribute(CPContentWebKeys.CP_CONTENT_HELPER);

		CPCatalogEntry cpCatalogEntry = cpContentHelper.getCPCatalogEntry(httpServletRequest);

		CPDefinition definition = _cPDefinitionService.getCPDefinition(cpCatalogEntry.getCPDefinitionId());

		if (!definition.getCPDefinitionOptionRels().isEmpty()) {
			_jspRenderer.renderJSP(_servletContext, httpServletRequest, httpServletResponse, "/options-view.jsp");
		} else {
			_jspRenderer.renderJSP(_servletContext, httpServletRequest, httpServletResponse,
				"/view.jsp");
		}
	}

	@Reference
	private CPDefinitionService _cPDefinitionService;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference(
			target = "(osgi.web.symbolicname=com.liferay.training.product.list.renderer)"
	)
	private ServletContext _servletContext;

}