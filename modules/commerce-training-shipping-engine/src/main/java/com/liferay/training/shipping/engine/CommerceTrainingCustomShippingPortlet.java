package com.liferay.training.shipping.engine;


import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.json.JSONException;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;

import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.exception.CommerceShippingEngineException;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceShippingEngine;
import com.liferay.commerce.model.CommerceShippingOption;
import com.liferay.commerce.service.CommerceAddressRestrictionLocalService;
import com.liferay.commerce.service.CommerceShippingMethodLocalService;
import com.liferay.commerce.shipping.engine.fixed.service.CommerceShippingFixedOptionLocalService;
import com.liferay.commerce.util.CommerceShippingHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

/**
 * It is important to provide a distinct key for the shipping engine so that
 * Liferay Commerce can distinguish the new engine from others in the shipping
 * engine registry. Reusing a key that is already in use will override the
 * existing associated engine.
 */
@Component(
		immediate = true, 
        property = "commerce.shipping.engine.key=" + CommerceTrainingCustomShippingPortlet.KEY, 
        service = CommerceShippingEngine.class
        )
public class CommerceTrainingCustomShippingPortlet implements CommerceShippingEngine {

	private static final String JSON_SHIPPING_COST = "shippingCost";

	private static final String SHIPPING_SERVICE_ENDPOINT = "shippingByPallet";
	private static final String SHIPPING_SERVICE_HOST = "http://2972b566-6b3f-44b0-91c2-0badbac7677e.mock.pstmn.io/";

	public static final String KEY = "devcon-2021-shipping";


	/**
	 * This method returns a text label used for shipping options. See the
	 * implementation in CommerceTrainingShippingEngine.java for a reference in
	 * retrieving the description with a language key.
	 */
	@Override
	public String getCommerceShippingOptionLabel(String name, Locale locale) {

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle("content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, name);
	}

	/**
	 * This will be where we add the business logic for our custom shipping engine.
	 * It must fetch a list of available options, then perform the processing
	 * necessary to present them to the customer.
	 */
	@Override
	public List<CommerceShippingOption> getCommerceShippingOptions(CommerceContext commerceContext,
			CommerceOrder commerceOrder, Locale locale) throws CommerceShippingEngineException {

		List<CommerceShippingOption> commerceShippingOptions = new ArrayList<>();

		// Get commerce order details
		String city = null;
		try {
			// For this example, we only care about the city
			city = commerceOrder.getShippingAddress().getCity();
		} catch (PortalException e1) {
			_log.error(e1.getMessage(), e1);
		}

		// Call external shipping engine RESTFull service
		ClientResource resource = new ClientResource(SHIPPING_SERVICE_HOST + SHIPPING_SERVICE_ENDPOINT);

		// If we have city, add it to the request
		if (city != null) {
			resource.addQueryParameter("city", city.toLowerCase().trim());
		}
		Representation representation = resource.get();

		// Process the services response
		JsonRepresentation jsonRepr;
		if (representation.isAvailable()) {
			try {
				jsonRepr = new JsonRepresentation(representation);

				// If we get an answer, we can add a new shipping option
				Integer value = (Integer) jsonRepr.getJsonObject().get(JSON_SHIPPING_COST);
				if (value != null) {

					if (_log.isDebugEnabled()) {
						_log.debug("Shipping response received with shipping cost = " + value);
					}

					BigDecimal amount = new BigDecimal(value);

					String optionName = getCommerceShippingOptionLabel(KEY, locale);
					CommerceShippingOption externalShippingOption = new CommerceShippingOption(optionName, optionName,
							amount);
					commerceShippingOptions.add(externalShippingOption);

				}
	
			} catch (IOException | JSONException e) {
				_log.error(e.getMessage(), e);
			}
		}


		return commerceShippingOptions;
	}

	@Override
	public String getDescription(Locale locale) {

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle("content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, "devcon-2021-shipping-desc");
	}

	@Override
	public String getName(Locale locale) {

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle("content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, "devcon-2021-shipping-name");
	}


	private static final Log _log = LogFactoryUtil.getLog(CommerceTrainingCustomShippingPortlet.class);

	@Reference
	private CommerceAddressRestrictionLocalService _commerceAddressRestrictionLocalService;

	@Reference
	private CommerceShippingFixedOptionLocalService _commerceShippingFixedOptionLocalService;

	@Reference
	private CommerceShippingHelper _commerceShippingHelper;

	@Reference
	private CommerceShippingMethodLocalService _commerceShippingMethodLocalService;

}