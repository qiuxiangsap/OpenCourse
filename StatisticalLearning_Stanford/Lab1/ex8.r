library(MASS)
attach(Auto)
lm.fit = lm(mpg ~ horsepower, data=Auto)
preds = predict(lm.fit, data.frame(horsepower=c(98)), interval="confidence", level=0.95)