getColumnName = function(x) {
  if (x == "heart attack") {
    return (11)
  } else if (x == "heart failure") {
    return (17)
  } else {
    return (23)
  }
}
best = function(state, outcome) {
  data = read.csv("outcome-of-care-measures.csv", colClasses="character")
  
  if (!any(data["State"] == state)) {
    stop("invalid state") 
  }
  if (! any(c("heart attack", "heart failure", "pneumonia") == outcome)) {
    stop("invalid outcome") 
  }
  
  selected = data[data["State"] == state & data[getColumnName(outcome)] != "Not Available", c(2,getColumnName(outcome))]
  
  r = order(as.numeric(selected[,2]), selected[,1])
  return (as.vector(selected[r[1],1]))
    
}