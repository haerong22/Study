package org.example.mvc.view;

import static org.example.mvc.view.RedirectView.*;

public class JspViewResolver implements ViewResolver {
    @Override
    public View resolveView(String viewName) {

        if (viewName.startsWith(DEFAULT_REDIRECT_PREFIX)) {
            return new RedirectView(viewName);
        }

        return new JspView(viewName + ".jsp");
    }
}
