states = row.names(USArrests)
apply(USArrests, 2, mean)
apply(USArrests, 2, var)
prout = prcomp(USArrests, scale=TRUE)
dim(prout$x)
biplot(prout, scale = 0)
prout$rotation = -prout$rotation
prout$x = -prout$x
biplot(prout, scale =0)
prout$sdev
pr.var = prout$sdev^2
pve = pr.var / sum(pr.var)
plot(pve, xlab="Principal Component", ylab = "Proportion of Variance Explained ", ylim=c(0,1), type='b')
plot(cumsum(pve), xlab="Principal Compoent", ylab="Cumulative Proportion of Variance Explained ", ylim=c(0,1), type='b')