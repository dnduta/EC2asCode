package com.demo;

import com.demo.aws.LaunchEc2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RunController {

    @Autowired
    private LaunchEc2 launchEc2;

    @GetMapping("/launch")
    private String launch()
    {
        launchEc2.launch();
        return "Scalable EC2 instances launched!";
    }
}
