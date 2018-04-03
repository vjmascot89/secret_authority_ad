package com.trending.game.model;

public enum TeamsStatus {
	DRAW(0),
Won(1),
Lost(2),
NOT_AVAILABLE(3);
	private Integer state;

	TeamsStatus(Integer state){
		this.state = state;
		
	}
	public static TeamsStatus valueOf(Integer state){
		for(TeamsStatus tstats:values()){
			if(tstats.state==state){
				return tstats;
			}
		}
		return null;
	}
}
