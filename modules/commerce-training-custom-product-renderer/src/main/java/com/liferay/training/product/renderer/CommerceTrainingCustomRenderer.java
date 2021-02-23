package com.liferay.training.product.renderer;


import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.commerce.product.catalog.CPCatalogEntry;
import com.liferay.commerce.product.content.render.CPContentRenderer;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;

/**
 * It is important to provide a distinct key for the product content renderer so
 * that Liferay Commerce can distinguish the new renderer from others in the
 * product content renderer registry. Reusing a key that is already in use will
 * override the existing associated renderer. The
 * commerce.product.content.renderer.order property determines the ordering of
 * renderers in the UI, from lowest to highest value. For example, the
 * SimpleCPContentRenderer has this property set to the minimum integer value,
 * so other renderers for Simple type products will appear after it in the list.
 * The commerce.product.content.renderer.type property determines what type of
 * product this renderer can be used for. In our example, we use a Simple type,
 * so the renderer will appear under the Simple category in the UI.
 * 
 * @author Filipe Afonso
 */
@Component(immediate = true, property = {
	"commerce.product.content.renderer.key=" + CommerceTrainingCustomRenderer.KEY,
    "commerce.product.content.renderer.order=" + 1,
	"commerce.product.content.renderer.type=grouped",
	"commerce.product.content.renderer.type=simple",
	"commerce.product.content.renderer.type=virtual"
}, service = CPContentRenderer.class)
public class CommerceTrainingCustomRenderer implements CPContentRenderer {

	public static final String KEY = "product-renderer-devcon";

	/**
	 * This method provides a unique identifier for the product content renderer
	 * in the product content renderer registry. The key can be used to fetch
	 * the renderer from the registry. Reusing a key that is already in use will
	 * override the existing associated renderer.
	 */
	@Override
	public String getKey() {

		return KEY;
	}

	/**
	 * This returns a text label that describes the product content renderer.
	 * See the implementation in CustomCPContentRenderer.java for a reference in
	 * retrieving the label with a language key.
	 */
	@Override
	public String getLabel(Locale locale) {
		// You should be using a localized label, but leveraging the resource bundles
		return KEY;
	}

	@Override
	public void render(
		CPCatalogEntry cpCatalogEntry, HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse)
		throws Exception {

		httpServletRequest.setAttribute("cpCatalogEntry", cpCatalogEntry);

		Double latitude = (Double) _cPDefinitionService.getCPDefinition(cpCatalogEntry.getCPDefinitionId())
				.getExpandoBridge().getAttribute("Latitude");
		Double longitude = (Double) _cPDefinitionService.getCPDefinition(cpCatalogEntry.getCPDefinitionId())
				.getExpandoBridge().getAttribute("Longitude");

		httpServletRequest.setAttribute("latitude", latitude != null ? latitude : 0.0);
		httpServletRequest.setAttribute("longitude", longitude != null ? longitude : 0.0);

		_jspRenderer.renderJSP(
			_servletContext, httpServletRequest, httpServletResponse,
			"/view.jsp");

	}

	@Reference
	private CPDefinitionService _cPDefinitionService;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference(target = "(osgi.web.symbolicname=com.liferay.training.product.renderer)")
	private ServletContext _servletContext;



}