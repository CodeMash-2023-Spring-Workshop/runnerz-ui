package org.codemash.runnerz.ui.views.runlist;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.codemash.runnerz.ui.model.Run;
import org.codemash.runnerz.ui.service.RunnerService;
import org.codemash.runnerz.ui.views.MainLayout;
import org.hibernate.validator.internal.util.classhierarchy.Filters;

import java.util.Arrays;

@PageTitle("Run List")
@Route(value = "run-list", layout = MainLayout.class)
public class RunListView extends Div implements AfterNavigationObserver {

    private Grid<Run> grid = new Grid<>();
    
    private Filters filters;
    private RunnerService runnerService;

    public RunListView(RunnerService runnerService) {
        this.runnerService = runnerService;
        addClassName("run-list-view");
        setSizeFull();
        grid.setHeight("100%");
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS);
        grid.addComponentColumn(run -> createRun(run));
        add(grid);
    }

    private HorizontalLayout createRun(Run run) {
        HorizontalLayout card = new HorizontalLayout();
        card.addClassName("run");
        card.setSpacing(false);
        card.getThemeList().add("spacing-s");

        VerticalLayout description = new VerticalLayout();
        description.addClassName("description");
        description.setSpacing(false);
        description.setPadding(false);
        
        HorizontalLayout header = new HorizontalLayout();
        header.addClassName("header");
        header.setSpacing(false);
        header.getThemeList().add("spacing-s");

        Span name = new Span(run.getTitle());
        name.addClassName("Title");
        Span start = new Span(run.getStartedOn().toString());
        start.addClassName("Started");
        Span finish = new Span(run.getStartedOn().toString());
        finish.addClassName("Finished");
        header.add(name);

        Span miles = new Span(run.getMiles().toString());
        miles.addClassName("Miles");
        
        Span location = new Span(run.getLocation().name());
        location.addClassName("location");
        
        description.add(header,start,finish, miles, location);
        card.add(description);
        return card;
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        Run[] runs = runnerService.getAll();
        grid.setItems(runs);
    }

}
