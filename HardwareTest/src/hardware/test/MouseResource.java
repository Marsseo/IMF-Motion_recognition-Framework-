package hardware.test;

import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.awt.Toolkit;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MouseResource extends CoapResource {
    //Field

    private static final Logger logger = LoggerFactory.getLogger(MouseResource.class);
    private int acclX;
    private int acclY;
    private int acclZ;

    public MouseResource() throws Exception {
        super("mouse");
    }

    public void move(int acclX, int acclY, int acclZ) throws Exception {
        // 현재 마우스 포인터의 위치
        PointerInfo a = MouseInfo.getPointerInfo();
        Point p = a.getLocation();
        int x = p.x;
        int y = p.y;

        // 모니터화면의 해상도 얻기
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screen.height;
        int width = screen.width;

        // 마우스 속도 조절 / 0 ~ 20단계 / 상하좌우 변경
        acclX /= -640;
        acclY /= -640;

        // 센서를 조금 움직일 경우 작동하지 않음
        if (Math.abs(acclX) < 5) {
            acclX = 0;
        } else if (acclX > 5) {
            acclX -= 5;
        } else if (acclX < -5) {
            acclX += 5;
        }
        if (Math.abs(acclY) < 5) {
            acclY = 0;
        } else if (acclY > 5) {
            acclY -= 5;
        } else if (acclY < -5) {
            acclY += 5;
        }
//        System.out.println("acclX  " + acclX + "acclY  " + acclY);
        // 기존 위치에서 변경된 값만큼 변경시킴
        int moveToX = x + acclX;
        int moveToY = y + acclY;

        if (moveToX < 0) {
            moveToX = 0;
        } else if (moveToX > width) {
            moveToX = width;
        }
        if (moveToY < 0) {
            moveToY = 0;
        } else if (moveToY > height) {
            moveToY = height;
        }

        // 마우스 이동
        Robot robot = new Robot();
        robot.mouseMove(moveToX, moveToY);
        System.out.println(x + ", " + y);
    }

    //Method
    @Override
    public void handleGET(CoapExchange exchange) {
    }

    @Override
    public void handlePOST(CoapExchange exchange) {
        //{ "command":"change", "direction":"forward", "speed":"1000" }
        //{ "command":"status" }
        try {
            String requestJson = exchange.getRequestText();
            JSONObject requestJsonObject = new JSONObject(requestJson);
            String command = requestJsonObject.getString("command");
            if (command.equals("change")) {
                acclX = Integer.parseInt(requestJsonObject.getString("acclX"));
                acclY = Integer.parseInt(requestJsonObject.getString("acclY"));
                acclZ = Integer.parseInt(requestJsonObject.getString("acclZ"));
                move(acclX, acclY, acclZ);
            } else if (command.equals("status")) {
            }
            JSONObject responseJsonObject = new JSONObject();
            responseJsonObject.put("result", "success");
            responseJsonObject.put("acclX", String.valueOf(acclX));
            responseJsonObject.put("acclY", String.valueOf(acclY));
            responseJsonObject.put("acclZ", String.valueOf(acclZ));
            String responseJson = responseJsonObject.toString();
            exchange.respond(responseJson);
        } catch (Exception e) {
            logger.info(e.toString());
            JSONObject responseJsonObject = new JSONObject();
            responseJsonObject.put("result", "fail");
            String responseJson = responseJsonObject.toString();
            exchange.respond(responseJson);
        }
    }
}
