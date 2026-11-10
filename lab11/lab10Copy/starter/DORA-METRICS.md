# Part C – DORA Metrics Baseline

## Current Pipeline Baseline

**Measurement period:** [Enter the dates you measured]

### 1. Deployment Frequency

Deployment frequency measures how often the application is successfully deployed.

- Successful deployments during measurement period: 9
- Measurement period: 10 days
- Deployment frequency: 0.9 deployments per day

Calculation:

`Successful deployments ÷ measurement period = deployment frequency`

### 2. Approximate Lead Time

Lead time measures the time between committing a code change and successfully deploying it.

I used the commit timestamp and the completion time of the corresponding successful GitHub Actions deployment to estimate lead time.

- Commit timestamp: 5:17am, 10/11/26.
- Successful deployment completion: 5:19am, 10/11/26.
- Approximate lead time: Four minutes.

## Pipeline Improvement

I enabled GitHub Actions BuildKit layer caching in the Docker build-and-push step using the GitHub Actions cache (`type=gha`).

This allows Docker to reuse unchanged image layers during subsequent builds instead of rebuilding them each time.

At the time of adding the change, Docker wansn't correctly added, so I don't know how much the deployment improved.

### Expected Impact

Caching should reduce Docker image build time on repeated deployments. This can reduce the time required to complete the deployment pipeline and potentially improve lead time.