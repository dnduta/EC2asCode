package com.demo.aws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.elasticloadbalancingv2.ElasticLoadBalancingV2Client;
import software.amazon.awssdk.services.elasticloadbalancingv2.model.*;

import java.util.Iterator;

@Component
public class CreateLoadBalancer {

    @Autowired
    private ElasticLoadBalancingV2Client loadBalancingV2Client;

    @Value("${aws.vpc}")
    private String vpcID;

    @Value("${aws.subnets}")
    private String[] subnets;
    @Value("${aws.secGrpName}")
    private String secGrpName;

    private String lbName = "demoAwsLB";
    private String tgName = "demoAwsTG";
    private Integer tgPort = 3000;
    private Integer listenerPort = 80;
    // returned values
    public String lbArn;
    public String tgArn;

    public void createLB()
    {
        CreateLoadBalancerRequest request = CreateLoadBalancerRequest.builder()
                .subnets(subnets)
                .name(lbName)
                .securityGroups(secGrpName)
                .build();

        CreateLoadBalancerResponse loadBalancerResponse = loadBalancingV2Client.createLoadBalancer(request);
        Iterator<LoadBalancer> it = loadBalancerResponse.loadBalancers().iterator();
        while (it.hasNext())
        {
            LoadBalancer lb = it.next();
            lbArn = lb.loadBalancerArn();
        }
    }

    public void targetGroup()
    {
        CreateTargetGroupRequest targetGroupRequest = CreateTargetGroupRequest.builder()
                .name(tgName)
                .port(tgPort)
                .vpcId(vpcID)
                .protocol(ProtocolEnum.HTTP)
                .build();

        CreateTargetGroupResponse response = loadBalancingV2Client.createTargetGroup(targetGroupRequest);


        Iterator<TargetGroup> grps = response.targetGroups().iterator();
        while(grps.hasNext())
        {
            TargetGroup tg = grps.next();
            tgArn = tg.targetGroupArn();
        }

    }

    public void listener()
    {
        Action action = Action.builder()
                .targetGroupArn(tgArn)
                .type(ActionTypeEnum.FORWARD)
                .build();
        CreateListenerRequest listenerRequest  = CreateListenerRequest.builder()
                .defaultActions(action)
                .loadBalancerArn(lbArn)
                .protocol(ProtocolEnum.HTTP)
                .port(listenerPort)
                .build();

        CreateListenerResponse listenerResponse = loadBalancingV2Client.createListener(listenerRequest);
    }

}
