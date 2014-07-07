getColumnName = function(x) {
  if (x == "heart attack") {
    return (11)
  } else if (x == "heart failure") {
    return (17)
  } else {
    return (23)
  }
}

rankone = function(state, outcome, num = "best") {
  data = read.csv("outcome-of-care-measures.csv", colClasses="character")
  
  if (!any(data["State"] == state)) {
    stop("invalid state") 
  }
  if (! any(c("heart attack", "heart failure", "pneumonia") == outcome)) {
    stop("invalid outcome") 
  }
  
  
  
  selected = data[data["State"] == state & data[getColumnName(outcome)] != "Not Available", c(2,getColumnName(outcome))]
  
  r = order(as.numeric(selected[,2]), selected[,1])
  if (num == "best") {
    num = 1
  }
  if (num == "worst") {
    num = length(r)
  }
  

  if (length(r) == 0) {
    return (NA)
  }
  return ( selected[r[as.numeric(num)],1])
  
}

rankall = function(outcome, num = "best") {
  data = read.csv("outcome-of-care-measures.csv", colClasses="character")
  states = (unique(data["State"]))
  
  res = data.frame()
  idx = 1
  for (state in sort(states[,1])) {
 
    hospital = rankone(state, outcome, num)
    res[idx,"hospital"] = hospital
    res[idx,"state"] = state
    idx = idx + 1
  }
  return (res)
}