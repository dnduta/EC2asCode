package com.demo.aws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LaunchEc2 {

    @Autowired
    private CreateLoadBalancer loadBalancer;

    @Autowired
    private ScalableEc2 scalableEc2;

    public void launch()
    {
        // Create load balancer
        loadBalancer.createLB();
        loadBalancer.targetGroup();
        loadBalancer.listener();

        // Create instances in a scalanle way
        scalableEc2.launchConfig();
        scalableEc2.autoScalingGroup(loadBalancer.tgArn);
        scalableEc2.autoScalingPolicy();

    }
}
