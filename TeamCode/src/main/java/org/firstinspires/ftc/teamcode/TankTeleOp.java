package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp
public class TankTeleOp extends LinearOpMode {
    @Override
    public void runOpMode(){

        DcMotorEx motor_1 = hardwareMap.get(DcMotorEx.class, "motor-1");

        telemetry.addData("Me", 1);
        telemetry.addData("She", 2);
        telemetry.addData("motor-1 ", motor_1.getDeviceName());
        
        telemetry.update();

        waitForStart();

        while(opModeIsActive()) {
            for(int i=0; i<10000; i++) {
                telemetry.addData("Now", 10);
                telemetry.addData("Then", 20);
                telemetry.addData("Gamepad-1.x", gamepad1.x);
                telemetry.addData("Gamepad-2.x", gamepad2.x);
                telemetry.update();
                sleep(10);
            }
        }
    }
}

