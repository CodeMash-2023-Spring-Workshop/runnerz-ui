package org.codemash.runnerz.ui.views.helloworld;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.codemash.runnerz.ui.service.RunnerService;
import org.codemash.runnerz.ui.views.MainLayout;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@PageTitle("Hello World")
@Route(value = "hello", layout = MainLayout.class)
public class HelloWorldView extends HorizontalLayout {

    private TextField name;
    private Button sayHello;

    public HelloWorldView(RunnerService runnerService) {
        name = new TextField("Your name");
        sayHello = new Button("Say hello");
        sayHello.addClickListener(e -> {
            Notification.show(runnerService.hello(name.getValue()));
        });
        sayHello.addClickShortcut(Key.ENTER);

        setMargin(true);
        setVerticalComponentAlignment(Alignment.END, name, sayHello);

        add(name, sayHello);
    }

}
