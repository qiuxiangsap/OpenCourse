getFileName <- function(x) {
  if (x < 10 ){
    return (paste("00",as.character(x),".csv", sep=""))
  } else if (x >= 10 && x < 100) {
    return (paste("0", as.character(x), ".csv", sep=""))
  } else {
    return (paste(as.character(x), ".csv", sep=""))
  }
}
complete <- function(directory, id = 1:332) {
  
  pol_sum = 0
  num = 0
  
  res = data.frame()
  
  idx = 1
  for (i in id) {
    data <- read.csv(paste("./",directory,"/",getFileName(i),sep=""))
    no_na = sum(apply(data, 1, function(x) {
      if (any(is.na(x))) {
        return (0)
      } else {
        return (1)
      }
    }
    )
    )
    
    res[idx, "id"] = i
    res[idx, "nobs"] = no_na
    idx = idx + 1
    
  }
  return (res)
}







