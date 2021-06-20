package src;

public class AntRace implements AntFields {

	public static void main(String[] args) {

		AntField field = new AntField(FIELD10);

		Ant ant = new Ant(field, 0, 0, 1);

		Thread thread = new Thread(ant);

		thread.start();

		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}


		System.out.println(field.toString());
	}
}
