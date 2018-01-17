package commons;

public class FakeHistoryInfoProviderMaker {

	public FakeHistoryInfoProvider makeNewInfoProvider() {
		return new FakeHistoryInfoProvider(100);//100+Math.random()*15000.0);
	}

}
