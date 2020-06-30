package com.demo.aws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.autoscaling.AutoScalingClient;
import software.amazon.awssdk.services.autoscaling.model.*;
import software.amazon.awssdk.services.ec2.model.InstanceType;

@Component
public class ScalableEc2 {

    @Autowired
    private AutoScalingClient scalingClient;

    @Value("${aws.profile}")
    private String profile;
    @Value("${aws.keyName}")
    private String keyName;
    @Value("${aws.secGrpName}")
    private String secGrpName;

    private String imageId = "ami-0d28dedc747db42b1";;
    private String lcName = "AwsDemoLaunchConfig";
    private String userData = "IyEvYmluL2Jhc2gNCnN1ZG8gYXB0LWdldCB1cGRhdGUNCnN1ZG8gYXB0LWdldCAteSBpbnN0YWxsIGdpdA0KZ2l0IGNsb25lIGh0dHBzOi8vZ2l0aHViLmNvbS9yeWFubXVyYWthbWkvaGJmbC5naXQgL2hvbWUvYml0bmFtaS9oYmZsDQpjaG93biAtUiBiaXRuYW1pOiAvaG9tZS9iaXRuYW1pL2hiZmwNCmNkIC9ob21lL2JpdG5hbWkvaGJmbA0Kc3VkbyBucG0gaQ0Kc3VkbyBucG0gcnVuIHN0YXJ0";
    private String asgName = "AwsDemoAutoScaling";
    private String[] aZones = new String[]{"us-east-2a", "us-east-2b", "us-east-2c"};;
    private String policyName = "AwsDemoAutoScaling";
    private String policyType = "TargetTrackingScaling";


    public void launchConfig()
    {

        CreateLaunchConfigurationRequest launchConfigurationRequest = CreateLaunchConfigurationRequest.builder()
                .iamInstanceProfile(profile)
                .imageId(imageId)
                .instanceType(InstanceType.T2_MICRO.toString())
                .launchConfigurationName(lcName)
                .keyName(keyName)
                .securityGroups(secGrpName)
                .userData(userData)
                .build();

        CreateLaunchConfigurationResponse launchConfigurationResponse = scalingClient.createLaunchConfiguration(launchConfigurationRequest);
    }

    public void autoScalingGroup(String targetGroupArn)
    {
        CreateAutoScalingGroupRequest request = CreateAutoScalingGroupRequest.builder()
                .autoScalingGroupName(asgName)
                .availabilityZones(aZones)
                .targetGroupARNs(targetGroupArn)
                .launchConfigurationName(lcName)
                .maxSize(3)
                .minSize(1)
                .build();

        CreateAutoScalingGroupResponse response = scalingClient.createAutoScalingGroup(request);
    }

    public void autoScalingPolicy()
    {
        PutScalingPolicyRequest policyRequest = PutScalingPolicyRequest.builder()
                .adjustmentType("ChangeIn")
                .autoScalingGroupName(asgName)
                .policyName(policyName)
                .policyType(policyType)
                .targetTrackingConfiguration(TargetTrackingConfiguration.builder()
                        .targetValue(5d)
                        .predefinedMetricSpecification(PredefinedMetricSpecification.builder()
                                .predefinedMetricType(MetricType.ASG_AVERAGE_CPU_UTILIZATION)
                                .build())
                        .build())
                .build();
        scalingClient.putScalingPolicy(policyRequest);
    }


}
