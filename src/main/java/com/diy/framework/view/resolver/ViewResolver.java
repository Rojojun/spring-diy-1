package com.diy.framework.view.resolver;

import com.diy.framework.view.View;
import com.diy.framework.view.ViewType;

public interface ViewResolver {
    boolean getViewType(ViewType viewType);

    View resolveViewName(String viewName);
}
