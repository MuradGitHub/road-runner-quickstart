package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@TeleOp
public class TestAutoMotor extends LinearOpMode {
    DcMotorEx      motor_1;

    Telemetry.Item statusItem;
    Telemetry.Item powerItem;
    Telemetry.Item directionItem;
    Telemetry.Item positionItem;
    Telemetry.Item velocityItem;
    Telemetry.Item velocityTargetItem;
    Telemetry.Item targetItem;
    Telemetry.Item modeItem;
    Telemetry.Item zeroPowerItem;
    Telemetry.Item powerFloatItem;
    Telemetry.Item positionTolItem;
    Telemetry.Item isBusyItem;
    Telemetry.Item pdif_pItem;
    Telemetry.Item pdif_iItem;
    Telemetry.Item pdif_dItem;
    Telemetry.Item pdif_fItem;

    @Override
    public void runOpMode(){
        motor_1      = hardwareMap.get(DcMotorEx.class, "motor-1");

        initTelemetry();

        waitForStart();

        // runMotorCrescendo();
        // runMotorTargets();
        runMotorVelocities();

        while(opModeIsActive());
    }

    private void runMotorTargets() {
        int[] targets           = {0, 100, 200, 300, 400, 500, 600, 700, 800, 900, 1000};
        int   target_pause_time = 10000; // ms
        int   tracking_time     = 50;
        motor_1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor_1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor_1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor_1.setTargetPositionTolerance(1);
        for(int target: targets) {
            motor_1.setTargetPosition(target);
            motor_1.setPower(1.0);
            while(motor_1.isBusy()) {
                updateTelemetryMotor_1("motor_1 Busy", DcMotor.RunMode.RUN_TO_POSITION);
                sleep(tracking_time);
            }
            updateTelemetryMotor_1("motor_1 Not Busy", DcMotor.RunMode.RUN_TO_POSITION);
            sleep((long) (target_pause_time / 2.0));
            updateTelemetryMotor_1("motor_1 Not Busy", DcMotor.RunMode.RUN_TO_POSITION);
            sleep((long) (target_pause_time / 2.0));
        }
        while(opModeIsActive()) {
            updateTelemetryMotor_1("motor_1 Not Busy - Steady State",
                    DcMotor.RunMode.RUN_TO_POSITION);
            sleep((long) (target_pause_time / 2.0));
        }
    }

    private void runMotorVelocities() {
        int[] velocities        = {10, 20, 30, 40, 50, 60, 90, 120, 360, 720};
        int   target_pause_time = 10000; // ms
        int   tracking_time     = 50;
        motor_1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        motor_1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor_1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor_1.setTargetPositionTolerance(1);
        for(int velocity: velocities) {
            motor_1.setVelocity(velocity, AngleUnit.DEGREES);
            // motor_1.setPower(1.0);
            while(motor_1.isBusy()) {
                updateTelemetryMotor_1("motor_1 Busy",
                        DcMotor.RunMode.RUN_USING_ENCODER,
                        velocity);
                sleep(tracking_time);
            }
            updateTelemetryMotor_1("motor_1 Not Busy",
                    DcMotor.RunMode.RUN_USING_ENCODER,
                    velocity);
            sleep((long) (target_pause_time / 2.0));
            updateTelemetryMotor_1("motor_1 Not Busy - After Sleep",
                    DcMotor.RunMode.RUN_USING_ENCODER,
                    velocity);
            sleep((long) (target_pause_time / 2.0));
        }
        while(opModeIsActive()) {
            updateTelemetryMotor_1("motor_1 Not Busy - Steady State",
                    DcMotor.RunMode.RUN_USING_ENCODER,
                    velocities[velocities.length-1]);
            sleep((long) (target_pause_time / 2.0));
        }
    }

    private void runMotorCrescendo() {
        for(int i=0; i<120; i++) {
            if (opModeIsActive()) {
                motor_1.setPower((double) i/100.0);
                telemetry.addData("i ", i);
                telemetry.addData("Motor Power ", motor_1.getPower());
                telemetry.addData("gamepad-1.x", gamepad1.x);
                telemetry.addData("gamepad-2.x", gamepad2.x);
                telemetry.update();
                sleep(200);
            }
        }
    }

    private void initTelemetry() {
        // telemetry.addData("motor-1                 ", motor_1.getDeviceName());
        // telemetry.addData("Motor Manufacturer      ", motor_1.getManufacturer());
        // telemetry.addData("Motor Connection Info   ", motor_1.getConnectionInfo());
        // telemetry.addData("motor-1.getPortNumber() ", motor_1.getPortNumber());
        // telemetry.addData("motor-1.getMotorType()  ", motor_1.getMotorType());

        PIDFCoefficients pdif = motor_1.getPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER);

        statusItem            = telemetry.addData("status                                ",
                "Initializing Telemetry");
        powerItem             = telemetry.addData("motor-1.getPower()                    ",
                motor_1.getPower());
        directionItem         = telemetry.addData("motor-1.getDirection()                ",
                motor_1.getDirection());
        positionItem          = telemetry.addData("motor-1.getCurrentPosition()          ",
                motor_1.getCurrentPosition());
        velocityItem          = telemetry.addData("motor-1.getVelocity()                 ",
                motor_1.getVelocity(AngleUnit.DEGREES));
        velocityTargetItem    = telemetry.addData("motor-1: Target Velocity:             ",
                0.0);
        targetItem            = telemetry.addData("motor-1.getTargetPosition()           ",
                motor_1.getTargetPosition());
        modeItem              = telemetry.addData("motor-1.getMode()                     ",
                motor_1.getMode());
        zeroPowerItem         = telemetry.addData("motor-1.getZeroPowerBehavior()        ",
                motor_1.getZeroPowerBehavior());
        powerFloatItem        = telemetry.addData("motor-1.getPowerFloat()               ",
                motor_1.getPowerFloat());
        positionTolItem       = telemetry.addData("motor-1.getTargetPositionTolerance()  ",
                motor_1.getTargetPositionTolerance());
        isBusyItem            = telemetry.addData("motor-1.isBusy()                      ",
                motor_1.isBusy());
        pdif_pItem            = telemetry.addData("PDIF.p                                ",
                pdif.p);
        pdif_iItem            = telemetry.addData("PDIF.i                                ",
                pdif.i);
        pdif_dItem            = telemetry.addData("PDIF.d                                ",
                pdif.d);
        pdif_fItem            = telemetry.addData("PDIF.f                                ",
                pdif.f);

        telemetry.update();
    }

    private void updateTelemetryMotor_1(String status,
                                        DcMotor.RunMode runMode,
                                        double velocityTarget) {
        velocityTargetItem.setValue(velocityTarget);
        updateTelemetryMotor_1(status, runMode);
    }
    private void updateTelemetryMotor_1(String status, DcMotor.RunMode runMode) {
        PIDFCoefficients pdif = motor_1.getPIDFCoefficients(runMode);

        statusItem.setValue(status);
        powerItem.setValue(motor_1.getPower());
        directionItem.setValue(motor_1.getDirection());
        targetItem.setValue(motor_1.getTargetPosition());
        positionItem.setValue(motor_1.getCurrentPosition());
        velocityItem.setValue(motor_1.getVelocity(AngleUnit.DEGREES));
        modeItem.setValue(motor_1.getMode());
        zeroPowerItem.setValue(motor_1.getZeroPowerBehavior());
        powerFloatItem.setValue(motor_1.getPowerFloat());
        positionTolItem.setValue(motor_1.getTargetPositionTolerance());
        isBusyItem.setValue(motor_1.isBusy());
        pdif_pItem.setValue(pdif.p);
        pdif_iItem.setValue(pdif.i);
        pdif_dItem.setValue(pdif.d);
        pdif_fItem.setValue(pdif.f);

        telemetry.update();
    }
}

