package com.trending.game.enums;

public enum GameResult {
	DRAW(0),
Won(1),
Lost(2),
NOT_AVAILABLE(3);
	private Integer state;

	GameResult(Integer state){
		this.state = state;
		
	}
	public static GameResult valueOf(Integer state){
		for(GameResult tstats:values()){
			if(tstats.state==state){
				return tstats;
			}
		}
		return null;
	}
}
