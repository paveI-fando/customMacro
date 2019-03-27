package com.atlassian.tutorial.macro;

import com.atlassian.confluence.content.render.xhtml.ConversionContext;
import com.atlassian.confluence.macro.Macro;
import com.atlassian.confluence.macro.MacroExecutionException;
import com.atlassian.confluence.renderer.radeox.macros.MacroUtils;
import com.atlassian.confluence.util.velocity.VelocityUtils;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.webresource.api.assembler.PageBuilderService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;
import java.util.Map;

@Named
public class ColorPick implements Macro {

    private final static String NAME = "Name";
    private final static String TEMPLATE_NAME = "templates/content";
    private final static String WEB_RESOURCE_NAME = "com.atlassian.tutorial.colorPickMacro:colorPickMacro-resources";
    private final PageBuilderService pageBuilderService;

    @Autowired
    public ColorPick(@ComponentImport PageBuilderService pageBuilderService) {
        this.pageBuilderService = pageBuilderService;
    }

    public String execute(final Map<String, String> macroParameters, final String macroBody, final ConversionContext conversionContext) throws MacroExecutionException {
        pageBuilderService.assembler().resources().requireWebResource(WEB_RESOURCE_NAME);
        final Map context = MacroUtils.defaultVelocityContext();
        context.put(NAME, macroParameters.get(NAME));
        return VelocityUtils.getRenderedTemplate(TEMPLATE_NAME, context);
    }

    public BodyType getBodyType() {
        return BodyType.NONE;
    }

    public OutputType getOutputType() {
        return OutputType.BLOCK;
    }
}