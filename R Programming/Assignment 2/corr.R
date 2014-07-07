corr <- function(directory, threshold=0) {
  files <- dir(paste("./",directory,"/",sep=""))
  cor_res = vector()
  
  no_na = data.frame()
  idx = 1
  
  
  for (file in files) {
    data = read.csv(paste("./",directory,"/",file,sep=""))
    no_na = data[apply(data, 1, function(x) !any(is.na(x))),]
    
    if (dim(no_na)[1] > threshold) {
      
      cor_res[idx] = cor(no_na[,"sulfate"], no_na[,"nitrate"])
      idx = idx + 1
    }
  }
  
  return (cor_res)
}





