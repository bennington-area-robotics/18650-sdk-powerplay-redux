package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "armStuff (Blocks to Java)")
public class armStuff extends LinearOpMode {

    private DcMotor tiltM;
    private DcMotor liftM;
    private DcMotor armRotationM;
    private Servo collectorDriveS;
    private Servo collectorTiltS;

    int tiltTargetPos;
    int collectorDriverPos;
    int liftTargetPos;
    double collectorTiltPos;

    /**
     * Describe this function...
     */
    @Override
    public void runOpMode() {
        tiltM = hardwareMap.get(DcMotor.class, "tiltM");
        liftM = hardwareMap.get(DcMotor.class, "liftM");
        armRotationM = hardwareMap.get(DcMotor.class, "armRotationM");
        collectorDriveS = hardwareMap.get(Servo.class, "collectorDriveS");
        collectorTiltS = hardwareMap.get(Servo.class, "collectorTiltS");

        Initialize();
        waitForStart();
        if (opModeIsActive()) {
            //Run();
            while (opModeIsActive()) {
                Main_Loop();
            }
        }
    }

    /**
     * Describe this function...
     */
    private void Initialize() {
        Initialize_Motors_Servos();
    }
    private void Initialize_Motors_Servos() {
        tiltM.setDirection(DcMotorSimple.Direction.FORWARD);
        tiltM.setTargetPosition(tiltM.getCurrentPosition());
        tiltM.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        tiltTargetPos = tiltM.getTargetPosition();
        tiltM.setPower(0.75);
        liftM.setTargetPosition(liftM.getCurrentPosition());
        liftM.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftTargetPos = liftM.getTargetPosition();
        liftM.setPower(0.7);
    }

    /**
     * Describe this function...
     */
    private void Process_Inputs() {
        if (gamepad1.left_stick_button) {
            if (collectorDriverPos == 1) {
                collectorDriverPos = 0;
            } else {
                collectorDriverPos = 1;
            }
        }
        if (gamepad1.left_bumper) {
            collectorTiltPos += -0.05;
        }
        if (gamepad1.right_bumper) {
            collectorTiltPos += 0.05;
        }
        tiltTargetPos += gamepad1.left_stick_y;
        liftTargetPos += gamepad1.right_trigger - gamepad1.left_trigger;
    }

    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */


    /**
     *
     */
    private void Update_Motors_Servos() {
        tiltM.setTargetPosition(tiltTargetPos);
        liftM.setTargetPosition(liftTargetPos);
        armRotationM.setPower(gamepad1.left_stick_x);
        collectorDriveS.setPosition(collectorDriverPos);
        collectorTiltS.setPosition(collectorTiltPos);
    }

    /**
     * To be used later:
    private void Run() {
    }*/

    /**
     * Runs all functions
     */
    private void Main_Loop() {
        Process_Inputs();
        Update_Motors_Servos();
        Update_Telemetry();
    }

    /**
     * Telemetry
     */
    private void Update_Telemetry() {
        telemetry.addData("Tilt Motor Pos", tiltM.getCurrentPosition());
        telemetry.addData("Lift Motor Pos", liftM.getCurrentPosition());
        telemetry.addData("Rotation Motor Pos", armRotationM.getCurrentPosition());
        telemetry.addData("Collector Drive Pos", collectorDriveS.getPosition());
        telemetry.addData("Collector Tilt Pos", collectorDriveS.getPosition());
        telemetry.update();
    }
}