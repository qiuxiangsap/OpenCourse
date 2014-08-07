library(ISLR)
names(Smarket)
dim(Smarket)
cor(Smarket[,-9])
plot(Smarket$Volume)

glm.fit = glm(Direction~Lag1 + Lag2 + Lag3 + Lag4 + Lag5 + Volume, data=Smarket, family=binomial)
glm.probs = predict(glm.fit, type="response")
contrasts(Smarket$Direction)
glm.pred = rep("Down", 1250)
glm.pred[glm.probs > .5] = "Up"
table(glm.pred, Smarket$Direction)
mean(glm.pred == Smarket$Direction)

train = (Smarket$Year < 2005)
Smarket.2005 = Smarket[!train, ]
glm.fit = glm(Direction)