package Motion;

public class Action implements ActionInterface {

	@Override
	public void action(String finalMotion) {
		switch (finalMotion) {
			case "left":
				System.out.println("----------------------Left 모션 인식");
				break;
			case "right":
				System.out.println("----------------------Right 모션 인식");
				break;
			case "up":
				System.out.println("----------------------Up 모션 인식");
				break;
			case "down":
				System.out.println("----------------------Down 모션 인식");
				break;
			case "zigzag":
				System.out.println("----------------------ZigZag 모션 인식");
				break;
			case "circle":
				System.out.println("----------------------Circle 모션 인식");
				break;
				case "n":
				System.out.println("----------------------N 모션 인식");
				break;
			default:
				System.out.println("----------------------모션 매칭 실패");
				break;
		}
	}

}
