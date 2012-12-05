package com.xtremelabs.robolectric.res;

import org.w3c.dom.Node;
public class DimenResourceLoader extends XpathResourceXmlLoader implements ResourceValueConverter {

    private static final String[] UNITS = { "dp", "dip", "pt", "px", "sp" };
	
    private ResourceReferenceResolver<Float> dimenResolver = new ResourceReferenceResolver<Float>("dimen");

    public DimenResourceLoader(ResourceExtractor resourceExtractor) {
        super(resourceExtractor, "/resources/dimen");
    }

    public float getValue(int resourceId) {
        Float value = dimenResolver.getValue(resourceExtractor.getResourceName(resourceId));
        if (value == null) {
            System.out.println("WARN: no value found for dimension " + resourceExtractor.getResourceName(resourceId));
            return 0;
        }
        return value;
    }

    public float getValue(String resourceName, String packageName) {
        return getValue(resourceExtractor.getResourceId(resourceName, packageName));
    }

    @Override
    protected void processNode(Node node, String name, XmlContext xmlContext) {
        dimenResolver.processResource(name, node.getTextContent(), this, xmlContext.packageName);
    }

    @Override
    public Object convertRawValue(String rawValue) {
    	int end = rawValue.length();
    	for ( int i = 0; i < UNITS.length; i++ ) {
    		int index = rawValue.indexOf(UNITS[i]);
    		if ( index >= 0 && end > index ) {
    			end = index;
    		}
    	}
    	
        return Float.parseFloat(rawValue.substring(0, end));
    }
}
