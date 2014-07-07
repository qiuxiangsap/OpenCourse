getFileName <- function(x) {
  if (x < 10 ){
    return (paste("00",as.character(x),".csv", sep=""))
  } else if (x >= 10 && x < 100) {
    return (paste("0", as.character(x), ".csv", sep=""))
  } else {
    return (paste(as.character(x), ".csv", sep=""))
  }
}
pollutantmean <- function(directory, pollutant, id = 1:332) {
  
  pol_sum = 0
  num = 0
  
  for (i in id) {
    data <- read.csv(paste("./",directory,"/",getFileName(i),sep=""))
    no_na = data[pollutant][!is.na(data[pollutant])]
    pol_sum = pol_sum +  sum(no_na)
    num = num + length(no_na)
  }
  return (pol_sum / num)
  
}
