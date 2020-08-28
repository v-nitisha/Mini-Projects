public class GotBrain implements BrainInterface{

	@Override
	public char nextMove(int cardsValue) {
		// TODO Auto-generated method stub
		char nextMove = 'S';

		//int cardsValue = getTotalCardsValue();

		if (cardsValue < 999) {
			nextMove = 'H';
		}

		return nextMove;
	}

}
