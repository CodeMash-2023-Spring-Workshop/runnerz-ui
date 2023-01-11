package org.codemash.runnerz.ui.views.runform;

import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.codemash.runnerz.ui.model.Location;
import org.codemash.runnerz.ui.model.Run;
import org.codemash.runnerz.ui.service.RunnerService;
import org.codemash.runnerz.ui.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.web.client.RestTemplate;


@PageTitle("Run Form")
@Route(value = "run-form", layout = MainLayout.class)
@Uses(Icon.class)
public class RunFormView extends Div {

    private TextField title = new TextField("Title");
    private DateTimePicker startedOn = new DateTimePicker("Started On");
    private DateTimePicker completedOn = new DateTimePicker("Completed On");
    private IntegerField miles = new IntegerField("Miles");
    private Select<Location> location = new Select<>();
    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");

    private Binder<Run> binder = new Binder<>(Run.class);

    public RunFormView(RunnerService runnerService) {
        addClassName("run-form-view");
        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout());
        binder.bindInstanceFields(this);
        location.setLabel("Location");
        location.setItems(Location.values());
        
        clearForm();

        cancel.addClickListener(e -> clearForm());
        save.addClickListener(e -> {
            try{
                Run run = new Run();
                run.setTitle(title.getValue());
                run.setMiles(miles.getValue());
                run.setStartedOn(startedOn.getValue());
                run.setCompletedOn(completedOn.getValue());
                run.setLocation(location.getValue());
                
                Integer id = runnerService.createRun(run);
                Notification.show(binder.getBean().getClass().getSimpleName() + " details stored.");
                Notification.show("Saved: " + id);
                clearForm();
            }catch(Exception exception){
                System.err.println(exception.getMessage());  
                Notification.show("Oops");
            }
            
        });
    }

    private void clearForm() {
        binder.setBean(new Run());
    }

    private Component createTitle() {
        return new H3("A Single Run");
    }

    private Component createFormLayout() {
        VerticalLayout formLayout = new VerticalLayout();
        formLayout.add(title, startedOn, completedOn, miles, location);
        return formLayout;
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save);
        buttonLayout.add(cancel);
        return buttonLayout;
    }

}