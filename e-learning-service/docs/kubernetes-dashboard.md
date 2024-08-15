- 설치 
```shell
kubectl apply -f https://raw.githubusercontent.com/kubernetes/dashboard/v2.4.0/aio/deploy/recommended.yaml

kubectl get deployment -n kubernetes-dashboard
```

- kubectl proxy 설정
```shell
kubectl proxy
```

- 브라우저에서 접근
```shell
http://localhost:8001/api/v1/namespaces/kubernetes-dashboard/services/https:kubernetes-dashboard:/proxy
```

- 계정 생성

`service-account.yml`
```yaml
apiVersion: v1
kind: ServiceAccount
metadata:
  name: dashboard-admin
  namespace: kubernetes-dashboard
```
```shell
kubectl apply -f service-account.yaml
```

- 권한 부여

`cluster-role-binding.yml`
```yaml
apiVersion: rbac.authorization.k8s.io/v1
kind: ClusterRoleBinding
metadata:
  name: dashboard-admin
roleRef:
  apiGroup: rbac.authorization.k8s.io
  kind: ClusterRole
  name: cluster-admin
subjects:
- kind: ServiceAccount
  name: dashboard-admin
  namespace: kubernetes-dashboard
```
```shell
kubectl apply -f cluster-role-binding.yaml
```

- 토큰 발급
```shell
kubectl -n kubernetes-dashboard create token dashboard-admin
```