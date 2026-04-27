package com.diy.framework.view.resolver;

import com.diy.framework.view.JspView;
import com.diy.framework.view.RedirectView;
import com.diy.framework.view.View;
import com.diy.framework.view.ViewType;

public class JspViewResolver implements ViewResolver {
    private static final String REDIRECT_PREFIX = "redirect:";
    private static final String VIEW_PREFIX = "/";
    private static final String VIEW_SUFFIX = ".jsp";

    @Override
    public boolean getViewType(ViewType viewType) {
        return viewType == ViewType.JSP;
    }

    @Override
    public View resolveViewName(String viewName) {
        if (viewName.startsWith(REDIRECT_PREFIX)) {
            return new RedirectView(viewName.substring(REDIRECT_PREFIX.length()));
        }
        return new JspView(VIEW_PREFIX + viewName + VIEW_SUFFIX);
    }
}
