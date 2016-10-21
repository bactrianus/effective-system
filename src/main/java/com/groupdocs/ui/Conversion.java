package com.groupdocs.ui;

import com.groupdocs.conversion.config.ConversionConfig;
import com.groupdocs.conversion.converter.option.LoadOptions;
import com.groupdocs.conversion.converter.option.SaveOptions;
import com.groupdocs.conversion.converter.option.WordsSaveOptions;
import com.groupdocs.conversion.handler.ConversionHandler;
import com.groupdocs.foundation.utils.wrapper.stream.GroupDocsInputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@WebServlet("/convert")
public class Conversion extends HttpServlet {
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Path source = (Path) request.getSession().getAttribute("source");
        Path tmp = Files.createTempDirectory("groupdocs-conversion-");

        ConversionConfig conversionConfig = new ConversionConfig();
        conversionConfig.setStoragePath(source.getParent().toString());
        ConversionHandler conversionHandler = new ConversionHandler(conversionConfig);

        LoadOptions loadOptions = null;
        SaveOptions saveOptions = new WordsSaveOptions();

        GroupDocsInputStream converted = conversionHandler.convert(source.getFileName().toString(), saveOptions);
        // TODO Read data from `converted' and write to `result'.
        Path result = Files.createTempFile("groupdocs-conversion-result-", "output.docx");
        request.getSession().setAttribute("result", result);

        response.sendRedirect("download");

    }
}

